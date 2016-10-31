package com.gestorventas.clases;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.graphics.drawable.GradientDrawable;
import com.gestorventas.R;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Guille on 25/10/16.
 */

public class ClienteAdapter  extends ArrayAdapter<Cliente>{

    private static final int LAYOUT_ID = R.layout.pedido_cliente_item;

    private Map<String, GradientDrawable> typesToBackgrounds = new HashMap<String, GradientDrawable>();

    public ClienteAdapter(Context context, List<Cliente> objects){
        super(context, LAYOUT_ID, objects);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(LAYOUT_ID, parent, false);
        }

        TextView nameTV = (TextView) view.findViewById(R.id.cliente_nombre);
        TextView uniqueIdTV = (TextView) view.findViewById(R.id.cliente_id);


        Cliente cliente = (Cliente) getItem(position);

        nameTV.setText(cliente.getRazonSocial());
        uniqueIdTV.setText(cliente.getCodigo());


        return view;
    }

    @SuppressWarnings("deprecation")
    private void styleType(TextView textView, String type) {

        Resources resources = getContext().getResources();
        // choose a nice color for this type based on what it is

        GradientDrawable background = typesToBackgrounds.get(type);
        if (background == null) {
            String colorName = "type_color_" + type.toLowerCase(Locale.US);
            int colorId = resources.getIdentifier(colorName, "color", getContext().getPackageName());
            background = (GradientDrawable)resources.getDrawable(R.drawable.type_background).mutate();
            background.setColor(resources.getColor(colorId));
            typesToBackgrounds.put(type, background);
        }

        textView.setBackgroundDrawable(background);
    }
}
