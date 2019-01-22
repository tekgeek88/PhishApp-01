package carga.tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import carga.tcss450.uw.edu.phishapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnLoginFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment {

    private OnLoginFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Button b = (Button) v.findViewById(R.id.login_button_register);
        b.setOnClickListener(this::register);

        b = (Button) v.findViewById(R.id.login_button_signin);
        b.setOnClickListener(this::signin);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Credentials credentials = (Credentials) getArguments()
                    .getSerializable(getString(R.string.key_register_success));
            updateContent(credentials);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void register(View view) {
        if (mListener != null) {
            mListener.onRegisterClicked();
        }
    }

    public void signin(View view) {
        if (mListener != null) {
            boolean isValidated = true;
            TextView textview_email = getActivity().findViewById(R.id.login_textview_email);
            TextView textview_password = getActivity().findViewById(R.id.login_textview_password);
            String username = textview_email.getText().toString();

            // Any empty EditText fields
            if(TextUtils.isEmpty(textview_email.getText())) {
                textview_email.setError("Field cannot be empty");
                isValidated = false;
            } else if (!textview_email.getText().toString().contains("@")) {
                textview_email.setError("Invalid email address");
                isValidated = false;
            }

            if(TextUtils.isEmpty(textview_password.getText())) {
                textview_password.setError("Field cannot be empty");
                isValidated = false;
            }

            // Do not navigate to SuccessFragment unless login inputs are in the correct format.
            if (isValidated) {
                Credentials.Builder credBuilder = new Credentials.Builder(username, textview_password.getText().toString());
                mListener.onLoginSuccess(credBuilder.build(), "");
            }

        }
    }


    public void updateContent(Credentials credentials) {
        TextView textView_email = getActivity().findViewById(R.id.login_textview_email);
        textView_email.setText(credentials.getEmail());

        TextView textView_password = getActivity().findViewById(R.id.login_textview_password);
        textView_password.setText(credentials.getPassword());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLoginFragmentInteractionListener {

        void onLoginSuccess(Credentials credentials, String jwt);

        void onRegisterClicked();
    }
}

