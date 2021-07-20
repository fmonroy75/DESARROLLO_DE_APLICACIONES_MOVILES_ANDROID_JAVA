package com.example.walletcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomLVIng extends BaseAdapter {
    //public class CustomListAdapter extends BaseAdapter {
        private ArrayList<ListIngresos> listData;
        private LayoutInflater layoutInflater;

        public CustomLVIng(Context aContext, ArrayList<ListIngresos> listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
                holder = new ViewHolder();
                holder.headlineView = (TextView) convertView.findViewById(R.id.Ingresos);
                holder.reporterNameView = (TextView) convertView.findViewById(R.id.Monto);
                holder.reportedDateView = (TextView) convertView.findViewById(R.id.Fechapago);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.headlineView.setText("("+listData.get(position).getIdingresos()+") "+listData.get(position).getdescripC());
            holder.reporterNameView.setText("Monto: " + listData.get(position).getmonto());
            holder.reportedDateView.setText(listData.get(position).getfechapago());
            return convertView;
        }

        static class ViewHolder {
            TextView headlineView;
            TextView reporterNameView;
            TextView reportedDateView;
        }

}
