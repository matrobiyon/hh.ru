package tj.example.effectivemobile.auth.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.FragmentAuth2Binding

class Auth2Fragment : Fragment() {

    private lateinit var binding : FragmentAuth2Binding

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

        binding.otpTest.setOnDoneListener {
            binding.otpTest.setIsCorrect(true)
            binding.otpTest.text?.clear()
            findNavController().navigate(Auth2FragmentDirections.actionAuthOtpFragmentToSearchFragment())
//            findNavController().popBackStack(R.id.auth_otp_Fragment,true)
        }
    }

}