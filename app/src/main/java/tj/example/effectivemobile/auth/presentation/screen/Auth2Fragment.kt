package tj.example.effectivemobile.auth.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tj.example.effectivemobile.MainActivity
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.FragmentAuth2Binding

class Auth2Fragment : Fragment() {

    private lateinit var binding: FragmentAuth2Binding
    private val args by navArgs<Auth2FragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAuth2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.email.text = getString(R.string.email, args.email)

        binding.otpTest.setOnDoneListener {
            binding.otpTest.setIsCorrect(true)
            binding.otpTest.text?.clear()
            (requireActivity() as MainActivity).hasEnteredAccount = true
            findNavController().navigate(Auth2FragmentDirections.actionAuthOtpFragmentToSearchFragment())
//            findNavController().popBackStack(R.id.auth_otp_Fragment,true)
        }
    }

}