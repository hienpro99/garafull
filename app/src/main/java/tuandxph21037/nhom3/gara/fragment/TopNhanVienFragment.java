package tuandxph21037.nhom3.gara.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.Adapter.TopNhanVienAdapter;
import tuandxph21037.nhom3.gara.Adapter.TopXeAdapter;
import tuandxph21037.nhom3.gara.DAO.HoaDonDAO;
import tuandxph21037.nhom3.gara.Model.Top;
import tuandxph21037.nhom3.gara.Model.TopNhanVien;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopNhanVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopNhanVienFragment extends Fragment {
    ListView lv;
    ArrayList<TopNhanVien> list;
    TopNhanVienAdapter adapter;
    public TopNhanVienFragment() {
        // Required empty public constructor
    }


    public static TopNhanVienFragment newInstance() {
        TopNhanVienFragment fragment = new TopNhanVienFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topnhanvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvTopNhanVien);
        HoaDonDAO hoaDonDAO = new HoaDonDAO(getActivity());
        list = (ArrayList<TopNhanVien>) hoaDonDAO.getTopNhanVien();
        adapter = new TopNhanVienAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }
}