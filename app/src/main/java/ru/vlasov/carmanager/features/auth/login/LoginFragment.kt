package ru.vlasov.carmanager.features.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.NetworkUser
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentLoginBinding
import ru.vlasov.carmanager.features.NavigationProvider
import ru.vlasov.carmanager.features.auth.AuthState
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class LoginFragment : Fragment(){

    private var binding : FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModelImpl>()
    val args : LoginFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(LayoutInflater.from(context))


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.usernameLoginField?.setText(args.login)
        binding?.buttonToSignUp?.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment2()
            activity?.let{
                (it as NavigationProvider).navigateByAction(action)
            }
        }

        binding?.buttonLogin?.setOnClickListener{
            setLoading()
            viewModel.tryLogin(binding?.usernameLoginField?.text.toString(),
                    binding?.passwordLoginField?.text.toString())
        }

        viewModel.viewState.observe(viewLifecycleOwner){
            handleLoginResult(it)
        }
    }

    private fun setLoading(){
        binding?.loginProgressBar?.visibility = View.VISIBLE
        binding?.passwordLoginField?.isEnabled = false
        binding?.usernameLoginField?.isEnabled = false
    }

    private fun handleLoginResult(states : List<AuthState>) {
        binding?.loginProgressBar?.visibility = View.GONE
        clearHelpers()
        for(state in states){
            when(state){
                is AuthState.Fail.IncorrectPassword -> {
                    showPasswordHelper()
                    unlockInput()
                }

                is AuthState.Fail.IncorrectUsername -> {
                    showUsernameHelper()
                    unlockInput()
                }

                is AuthState.SuccessLogin -> {
                    unlockInput()
                    navigateToMainFragment()
                }

                is AuthState.Loading -> {
                    setLoading()
                }

                is AuthState.Fail.UserNotFound -> {
                    unlockInput()
                    showDialogUserNotFound()
                }

                is AuthState.Fail.NetworkError -> {
                    unlockInput()
                    activity?.let{
                        (it as NetworkUser).showNetworkErrorDialog()
                    }
                }

                is AuthState.Fail.UnexpectedState -> {
                    unlockInput()
                }

                else -> throw IllegalArgumentException("unknown login state")
            }
        }

    }
    private fun navigateToMainFragment(){
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        activity?.let{
            (it as NavigationProvider).navigateByAction(action)
        }
    }

    private fun showPasswordHelper() {
        binding?.passwordLoginBlock?.helperText = getString(R.string.password_helper_text)
    }

    private fun showUsernameHelper() {
        binding?.usernameLoginBlock?.helperText = getString(R.string.username_helper_text)
    }

    private fun clearHelpers() {
        binding?.passwordLoginBlock?.helperText = ""
        binding?.usernameLoginBlock?.helperText = ""
    }
    private fun unlockInput() {
        binding?.passwordLoginField?.isEnabled = true
        binding?.usernameLoginField?.isEnabled = true
    }
    private fun showDialogUserNotFound(){
        AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.user_not_found_message))
            .setPositiveButton(R.string.cancel) {
                dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
