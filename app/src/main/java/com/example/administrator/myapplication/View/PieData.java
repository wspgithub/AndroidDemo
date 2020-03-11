package com.example.administrator.myapplication.View;

public class PieData {

        /**
         * 文字描述
         */
        private String ValueText="";

        /**
         * 值
         */
        private float Value;

        /**
         * 起始角度
         */
        private float startAngel;

        /**
         * 扫过的角度
         */
        private float Angel;

        /**
         * 显示的颜色
         */
        private String color;

        public String getValueText() {
            return ValueText;
        }

        public void setValueText(String valueText) {
            ValueText = valueText;
        }

        public float getValue() {
            return Value;
        }

        public void setValue(float value) {
            Value = value;
        }

        public float getStartAngel() {
            return startAngel;
        }

        public void setStartAngel(float startAngel) {
            this.startAngel = startAngel;
        }

        public float getAngel() {
            return Angel;
        }

        public void setAngel(float angel) {
            Angel = angel;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }