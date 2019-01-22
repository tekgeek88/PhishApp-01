package carga.tcss450.uw.edu.phishapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import carga.tcss450.uw.edu.phishapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnRegisterFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegisterFragment extends Fragment {

    private OnRegisterFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        // Inflate the layout for this fragment

        Button b = (Button) v.findViewById(R.id.register_button_register);
        b.setOnClickListener(this::register);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSuccessFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void register(View view) {
        if (mListener != null) {
            boolean isValidated = true;
            TextView textview_email = getActivity().findViewById(R.id.register_textview_email);
            TextView textview_password = getActivity().findViewById(R.id.register_textview_password);
            TextView textview_password_confirm = getActivity().findViewById(R.id.register_textview_password_confirm);
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
            if(TextUtils.isEmpty(textview_password_confirm.getText().toString())) {
                textview_password_confirm.setError("Field cannot be empty");
                isValidated = false;
            }

            // Check for password equality
            if (!(textview_password.getText().toString().equals(textview_password_confirm.getText().toString()))) {
                textview_password.setError("Password fields do not match!");
                textview_password_confirm.setError("Password fields do not match!");
                isValidated = false;
            }


            // Check for passwords less than 6 characters
            if(textview_password.getText().toString().length() < 6) {
                textview_password.setError("Password cannot be less than 6 characters");
                isValidated = false;
            }
            if(textview_password_confirm.getText().toString().length() < 6) {
                textview_password_confirm.setError("Password cannot be less than 6 characters");
                isValidated = false;
            }

            // Do not navigate to SuccessFragment unless login inputs are in the correct format.
            if (isValidated) {
                Credentials.Builder credBuilder = new Credentials.Builder(username, textview_password.getText().toString());
                mListener.onRegisterSuccess(credBuilder.build());
            }
        }
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
    public interface OnRegisterFragmentInteractionListener {
        void onRegisterSuccess(Credentials credentials);
    }



}
