package tj.example.effectivemobile.auth.presentation.screen

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.FragmentAuth1Binding

class Auth1Fragment : Fragment() {

    private lateinit var binding: FragmentAuth1Binding
    private var isError = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAuth1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextAuth1.doOnTextChanged { text, start, before, count ->
            if ((binding.editTextAuth1.text?.length ?: 0) >= 1) {
                binding.buttonNext.isEnabled = true
                binding.clearText.isVisible = true
            } else {
                binding.buttonNext.isEnabled = false
                binding.clearText.isVisible = false
            }
            if (isError) {
                binding.editTextParent.setBackgroundResource(R.drawable.rounded_edittext_background)
                binding.enteredWrongEmail.isVisible = false
            }
        }

        binding.clearText.setOnClickListener {
            binding.editTextAuth1.text?.clear()
            if (isError) {
                binding.editTextParent.setBackgroundResource(R.drawable.rounded_edittext_background)
                binding.enteredWrongEmail.isVisible = false
            }
        }

        binding.buttonNext.setOnClickListener {
            if (binding.editTextAuth1.text?.toString()?.trim()?.isNotEmpty() == true) {
                if (isValidEmail(binding.editTextAuth1.text.toString().trim())) {
                    val directions =
                        Auth1FragmentDirections.actionAuthFragmentToAuthOtpFragment(email = binding.editTextAuth1.text.toString())
                    findNavController().navigate(directions,
//                        navOptions = NavOptions.Builder().setPopUpTo(R.id.authFragment, true)
//                            .build()
                    )

                } else {
                    isError = true
                    binding.editTextParent.setBackgroundResource(R.drawable.rounded_edit_text_error)
                    binding.enteredWrongEmail.isVisible = true
                }
            }
        }

    }

}

private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}