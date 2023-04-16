package com.romullodev.generic_expandable_adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.romullodev.generic_expandable_adapter.databinding.FragmentDefaultExpandableAdapterBinding
import com.romullodev.generic_expandable_adapter.utils.MockData
import com.github.romullodev.generic_expandable_adapter.utils.setupDefaultExpandableAdapter

class DefaultExpandableAdapterFragment : Fragment() {

    private var _binding: FragmentDefaultExpandableAdapterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDefaultExpandableAdapterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapterByDefault()
    }

    private fun setupAdapterByDefault() {
        binding.recyclerViewExpandableAdapterDemo.setupDefaultExpandableAdapter(
            //dataHeaders = MockData.getMusicsWithNoBackground()
            dataHeaders = MockData.getMusicsWithBackground()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}