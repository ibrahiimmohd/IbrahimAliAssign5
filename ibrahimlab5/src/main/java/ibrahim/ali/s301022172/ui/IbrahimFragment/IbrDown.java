package ibrahim.ali.s301022172.ui.IbrahimFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ibrahim.ali.s301022172.R;

public class IbrDown extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ibr_down, container, false);
        final TextView textView = root.findViewById(R.id.Ibrahim);

        return root;
    }
}