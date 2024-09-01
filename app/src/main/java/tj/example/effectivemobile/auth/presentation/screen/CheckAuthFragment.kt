package tj.example.effectivemobile.auth.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import tj.example.effectivemobile.MainActivity
import tj.example.effectivemobile.R

class CheckAuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasEntered = (requireActivity() as MainActivity).hasEnteredAccount

        if (hasEntered) {
            findNavController().navigate(
                CheckAuthFragmentDirections.actionCheckAuthFragmentToSearchFragment(),
                navOptions = NavOptions.Builder().setPopUpTo(R.id.checkAuthFragment, true).build()
            )
        } else {
            findNavController().navigate(CheckAuthFragmentDirections.actionCheckAuthFragmentToAuthFragment(),
                navOptions = NavOptions.Builder().setPopUpTo(R.id.checkAuthFragment, true).build()
                )

        }
    }

}