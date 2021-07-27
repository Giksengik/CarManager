package ru.vlasov.carmanager.features.auth.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.NetworkUser
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentSignUpBinding
import ru.vlasov.carmanager.features.NavigationProvider
import ru.vlasov.carmanager.features.auth.AuthState
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var binding : FragmentSignUpBinding? = null
    private val viewModel: SignUpViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(LayoutInflater.from(context))

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonToLogin?.setOnClickListener{
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            activity?.let{
                (it as NavigationProvider).navigateByAction(action)
            }
        }
        binding?.buttonSignUp?.setOnClickListener{
            setLoading()
            viewModel.trySignUp(binding?.usernameSignUpField?.text.toString(),
                    binding?.passwordSignUpField?.text.toString(),
                    binding?.emailSignUpField?.text.toString())
        }

        viewModel.viewState.observe(viewLifecycleOwner){
            handleSignUpResult(it)
        } 
    }

    private fun handleSignUpResult(states : List<AuthState>) {
        binding?.signUpProgressBar?.visibility = View.GONE
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

                is AuthState.Fail.IncorrectEmail -> {
                    showEmailHelper()
                    unlockInput()
                }

                is AuthState.SuccessSignUp -> {
                    unlockInput()
                    navigateToLoginWithArg(binding?.usernameSignUpField?.text?.toString())
                }

                is AuthState.Loading -> {
                    setLoading()
                }

                is AuthState.Fail.NetworkError -> {
                    unlockInput()
                    activity?.let{
                        (it as NetworkUser).showNetworkErrorDialog()
                    }
                }
                is AuthState.Fail.SignUp.WrongInput -> {
                    unlockInput()
                    showWrongInputDialog()
                }
                is AuthState.Fail.SignUp.EmailOccupied -> {
                    unlockInput()
                    showEmailOccupiedHelper()
                }
                is AuthState.Fail.SignUp.UsernameAlreadyExist ->{
                    unlockInput()
                    showUsernameIsAlreadyExistHelper()
                }

                is AuthState.Fail.UnexpectedState -> {
                    unlockInput()
                }

                else -> throw IllegalArgumentException("unknown login state")
            }
        }

    }

    private fun navigateToLoginWithArg(login : String?) {
        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment(login ?: "")
        activity?.let{
            (it as NavigationProvider).navigateByAction(action)
        }
    }

    private fun showWrongInputDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.wrong_input_message)
            .setPositiveButton(R.string.cancel) {
                dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showEmailOccupiedHelper() {
        binding?.emailSignUpBlock?.helperText = getString(R.string.email_occupied)
    }
    private fun showUsernameIsAlreadyExistHelper() {
        binding?.usernameSignUpBlock?.helperText = getString(R.string.username_already_exist)
    }

    private fun showPasswordHelper() {
        binding?.passwordSignUpBlock?.helperText = getString(R.string.password_helper_text)
    }

    private fun showUsernameHelper() {
        binding?.usernameSignUpBlock?.helperText = getString(R.string.username_helper_text)
    }

    private fun showEmailHelper() {
        binding?.emailSignUpBlock?.helperText = getString(R.string.email_helper_text)
    }

    private fun clearHelpers() {
        binding?.passwordSignUpBlock?.helperText = ""
        binding?.usernameSignUpBlock?.helperText = ""
        binding?.emailSignUpBlock?.helperText = ""
    }
    private fun unlockInput() {
        binding?.passwordSignUpField?.isEnabled = true
        binding?.usernameSignUpField?.isEnabled = true
        binding?.emailSignUpField?.isEnabled = true
    }

    private fun setLoading(){
        binding?.signUpProgressBar?.visibility = View.VISIBLE
        binding?.passwordSignUpField?.isEnabled = false
        binding?.usernameSignUpField?.isEnabled = false
        binding?.emailSignUpField?.isEnabled = false
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}