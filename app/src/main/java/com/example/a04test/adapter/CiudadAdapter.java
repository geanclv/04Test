package com.example.a04test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a04test.R;
import com.example.a04test.model.Ciudad;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CiudadAdapter extends RecyclerView.Adapter<CiudadAdapter.ViewHolder> {

    private Context context;
    private int layout;
    private List<Ciudad> lstCiudad;
    private CiudadAdapter.OnItemClickListener onItemClickListener;
    private CiudadAdapter.OnButtonClickListener onButtonClickListener;

    public CiudadAdapter(Context context, int layout, List<Ciudad> lstCiudad,
                         CiudadAdapter.OnItemClickListener onItemClickListener,
                         CiudadAdapter.OnButtonClickListener onButtonClickListener) {
        this.context = context;
        this.layout = layout;
        this.lstCiudad = lstCiudad;
        this.onItemClickListener = onItemClickListener;
        this.onButtonClickListener = onButtonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(lstCiudad.get(position), onItemClickListener, onButtonClickListener);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lstCiudad.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblCiudadNombre;
        TextView lblCiudadDescripcion;
        TextView lblCiudadEstrellas;
        ImageView imageViewCiudad;
        Button btnCiudadEliminar;

        View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;
            this.lblCiudadNombre = view.findViewById(R.id.lblCiudadNombre);
            this.lblCiudadDescripcion = view.findViewById(R.id.lblCiudadDescripcion);
            this.lblCiudadEstrellas = view.findViewById(R.id.lblCiudadEstrellas);
            this.imageViewCiudad = view.findViewById(R.id.imageViewCiudad);
            this.btnCiudadEliminar = view.findViewById(R.id.btnCiudadEliminar);
        }

        public void bind(final Ciudad ciudad,
                         final CiudadAdapter.OnItemClickListener onItemClickListener,
                         final CiudadAdapter.OnButtonClickListener onButtonClickListener){
            lblCiudadNombre.setText(ciudad.getNombre());
            lblCiudadDescripcion.setText(ciudad.getDescripcion());
            lblCiudadEstrellas.setText(ciudad.getEstrellas() + "");
            Picasso.get().load(ciudad.getImagen()).fit().into(imageViewCiudad);

            btnCiudadEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(ciudad, getAdapterPosition());
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(ciudad, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Ciudad ciudad, int posicion);
    }

    public interface OnButtonClickListener {
        void onButtonClick(Ciudad ciudad, int posicion);
    }
}
