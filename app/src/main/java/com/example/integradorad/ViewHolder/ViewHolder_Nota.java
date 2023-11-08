package com.example.integradorad.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradorad.R;

public class ViewHolder_Nota extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolder_Nota.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);  /*SE EJECUTA AL PRESIONAR EN EL ITEM*/
        void onItemLongClick(View view, int position); /*SE EJECUTA AL MANTENER PRESIONADO EN EL ITEM*/

    }

    public void setOnClickListener(ViewHolder_Nota.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public ViewHolder_Nota(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getBindingAdapterPosition());
                return false;
            }
        });
    }

    public void SetearDatos(Context context, String correoUsuario, String descripcion, String estado, String fecha,
                            String fechaHoraActual, String hora, String titulo, String uidUsuario){


        //DECLARAMOS LAS VISTAS
        TextView correousuariocitas_item, descripcion_item, estodo_item, fecha_item,
                fechaHoraActual_item, hora_item, titulo_item, uidUsuariocitas_item;

        //ESTABLECER CONEXION CON EL ITEM
        correousuariocitas_item = mView.findViewById(R.id.correousuariocitas_item);
        descripcion_item = mView.findViewById(R.id.descripcion_item);
        estodo_item = mView.findViewById(R.id.estodo_item);
        fecha_item = mView.findViewById(R.id.fecha_item);
        fechaHoraActual_item = mView.findViewById(R.id.fechaHoraActual_item);
        hora_item = mView.findViewById(R.id.hora_item);
        titulo_item = mView.findViewById(R.id.titulo_item);
        uidUsuariocitas_item = mView.findViewById(R.id.uidUsuariocitas_item);

        //SETEAR LA INFORMACION DENTRO DEL ITEM
        correousuariocitas_item.setText(correoUsuario);
        descripcion_item.setText("Descripcion: " + descripcion);
        estodo_item.setText("Estado: " + estado);
        fecha_item.setText("Fecha: " + fecha);
        fechaHoraActual_item.setText(fechaHoraActual);
        hora_item.setText("Hora: " + hora);
        titulo_item.setText(titulo);
        uidUsuariocitas_item.setText(uidUsuario);

    }
}
