package com.sample.memorynotes.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sample.core.data.Note

import com.sample.memorynotes.R
import com.sample.memorynotes.framework.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {
    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note(0L, "", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNode(noteId)
        }

        checkButton.setOnClickListener {
            if (titleView.text.toString().isNotEmpty()
                || contentView.text.toString().isNotEmpty()
            ) {
                val time = System.currentTimeMillis()
                currentNote.title = titleView.text.toString()
                currentNote.content = contentView.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.updateTime = time
                }
                viewModel.addNote(currentNote)
            }
            findNavController().popBackStack()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong. Please try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.currentNote.observe(this, Observer { note ->
            note?.let {
                currentNote = it
                titleView.setText(it.title, TextView.BufferType.EDITABLE)
                contentView.setText(it.content, TextView.BufferType.EDITABLE)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteNote -> {
                context?.let {
                    if (noteId != 0L) {
                        AlertDialog.Builder(context)
                            .setTitle("Delete Node")
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                                viewModel.deleteNode(currentNote)
                            }
                            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
                            .create()
                            .show()
                    }
                }
            }
        }
        return true
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken, 0)
    }

}
