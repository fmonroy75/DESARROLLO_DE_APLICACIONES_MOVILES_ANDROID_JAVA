package com.example.walletcontrol;

public class ListIngresos {
    //idingresos,descripC,monto,fechapago
        private int Idingresos;
        private String descripC;
        private int monto;
        private String fechapago;

        public int getIdingresos() {
            return Idingresos;
        }

        public void setIdingresos(int id) {
            this.Idingresos = id;
        }

         public int getmonto() {
            return monto;
        }

         public void setmonto(int m) {
            this.monto = m;
        }
        public String getdescripC() {
            return descripC;
        }

        public void setdescripC(String descripC) {
            this.descripC = descripC;
        }

        public String getfechapago() {
            return fechapago;
        }

        public void setfechapago(String fechapago) {
            this.fechapago = fechapago;
        }
}
