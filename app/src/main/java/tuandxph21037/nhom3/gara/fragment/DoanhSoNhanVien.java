package tuandxph21037.nhom3.gara.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import tuandxph21037.nhom3.gara.Adapter.HoaDonAdapter;
import tuandxph21037.nhom3.gara.Adapter.HoaDonDTAdapter;
import tuandxph21037.nhom3.gara.DAO.HoaDonDAO;
import tuandxph21037.nhom3.gara.Model.HoaDon;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhSoNhanVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhSoNhanVien extends Fragment {
    Button btnTuNgay, btnDenNgay, btnDoanhThu;
    EditText edTuNgay, edDenNgay;
    TextView tvDoanhThu, tvhd;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
    int mYear, mMonth, mDay;
    List<HoaDon> listHd;
    HoaDonDTAdapter adp;
    ListView lv;
    public DoanhSoNhanVien() {
        // Required empty public constructor
    }


    public static DoanhSoNhanVien newInstance() {
        DoanhSoNhanVien fragment = new DoanhSoNhanVien();
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
        return inflater.inflate(R.layout.fragment_doanhsonhanvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvHDonDT);
        edTuNgay = view.findViewById(R.id.edTuNgay);
        edDenNgay = view.findViewById(R.id.edDenNgay);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edTuNgay.getText().toString();
                String denNgay = edDenNgay.getText().toString();
                HoaDonDAO hoaDonDAO = new HoaDonDAO(getActivity());
                tvDoanhThu.setText("Doanh thu: "+hoaDonDAO.getDoanhThu(tuNgay, denNgay)+" VN??");
                listHd= new ArrayList<>();
                listHd=hoaDonDAO.getHoaDonDT(tuNgay,denNgay);
                adp= new HoaDonDTAdapter(getActivity(),DoanhSoNhanVien.newInstance(), (ArrayList<HoaDon>) listHd);
                lv.setAdapter(adp);
                tvhd= view.findViewById(R.id.tvhd);
                tvhd.setVisibility(View.VISIBLE);

            }
        });
    }
    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edTuNgay.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edDenNgay.setText(sdf.format(c.getTime()));
        }
    };
}