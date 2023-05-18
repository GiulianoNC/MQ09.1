package com.quantum.adaptador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantum.entidades.Contactos;
import com.quantum.mq09.R;
import com.quantum.mq09.VerActivity;

import java.util.ArrayList;


public class adaptador extends RecyclerView.Adapter  <adaptador.ContactoViewHolder>{

    //crear constructor para llamado
    ArrayList<Contactos> listaContactos;
    //prueba
    boolean correcto = false;
    int  id = 0;

    public adaptador(ArrayList<Contactos> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public adaptador.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item, null, false);
        return new ContactoViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull adaptador.ContactoViewHolder holder, int position) {
        final Contactos contactos = listaContactos.get(position);
        holder.viewItem.setText(listaContactos.get(position).getItem());
        holder.viewPallet.setText(listaContactos.get(position).getPallet());
        holder.ViewResultado.setText(listaContactos.get(position).getResultado());

        String resultado = listaContactos.get(position).getResultado();
        if(resultado.equals("error")){
            holder.ViewResultado.setBackgroundColor(contactos.isSelected() ? Color.RED : Color.RED);
            holder.ViewResultado.setTextColor(contactos.isSelected() ? Color.WHITE : Color.WHITE);

        }else if (resultado.equals("procesado")){
            holder.ViewResultado.setBackgroundColor(contactos.isSelected() ? Color.GREEN : Color.BLUE);
            holder.ViewResultado.setTextColor(contactos.isSelected() ? Color.WHITE : Color.WHITE);

        }else{
            holder.ViewResultado.setBackgroundColor(contactos.isSelected() ? Color.WHITE : Color.WHITE);
        }

        holder.ids.setText(listaContactos.get(position).getId() + "");

    }

    @Override
    public int getItemCount() {
        return listaContactos == null ? 0 :  listaContactos.size();
    }


    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        public TextView viewItem, viewPallet,ViewResultado,ids;
        private View view;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewItem = itemView.findViewById(R.id.viewItem);
            viewPallet = itemView.findViewById(R.id.viewPallet);
            ViewResultado = itemView.findViewById(R.id.viewResultado);

            ids = itemView.findViewById(R.id.IDtext);
            this.view = itemView;

            //para seleccionar una determinada seleccion de serie y codigo
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());

                    context.startActivity(intent);


                }
            });

        }
    }
}
