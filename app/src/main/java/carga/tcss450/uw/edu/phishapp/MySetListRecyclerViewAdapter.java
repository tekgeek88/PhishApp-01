package carga.tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import carga.tcss450.uw.edu.phishapp.setlist.SetList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SetList} and makes a call to the
 * specified {@link SetListFragment.OnSetListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySetListRecyclerViewAdapter extends RecyclerView.Adapter<MySetListRecyclerViewAdapter.ViewHolder> {

    private final List<SetList> mValues;
    private final SetListFragment.OnSetListFragmentInteractionListener mListener;

    public MySetListRecyclerViewAdapter(List<SetList> items, SetListFragment.OnSetListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_setlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mSetList = mValues.get(position);
        holder.mLongDate.setText(mValues.get(position).getLongDate());
        holder.mLocation.setText(mValues.get(position).getLocation());
        holder.mVenue.setText(Html.fromHtml(mValues.get(position).getVenue(),
                Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE, null, null));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSetListFragmentInteraction(holder.mSetList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLongDate;
        public final TextView mLocation;
        public final TextView mVenue;
        public SetList mSetList;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLongDate = (TextView) view.findViewById(R.id.textview_fragment_setlist_long_date);
            mLocation = (TextView) view.findViewById(R.id.textview_fragment_setlist_location);
            mVenue = (TextView) view.findViewById(R.id.textview_fragment_setlist_venue);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocation.getText() + "'";
        }
    }
}
