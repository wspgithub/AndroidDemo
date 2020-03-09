package com.example.administrator.myapplication.View;

import android.graphics.Matrix;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wsp on 2019/3/22.
 */

public class MapStatusSave {

    private static HashMap<String,MapStatus> hashMap = new HashMap<>();

    public static HashMap<String, MapStatus> getHashMap() {
        return hashMap;
    }

    public static class MapStatus{
        private List<MapBaseLayer> layers = new ArrayList<>();
        private Matrix saveMatrix = new Matrix();
        private Matrix currentMatrix = new Matrix();
        private PointF startTouch = new PointF();
        private PointF mid = new PointF();
        private float currentZoom;
        private float saveZoom;
        private float currentRotateDegrees;
        private float oldDist = 0;
        /**地图移动的最高点用于边界控制**/
        private float LeftTopX = 0;
        /**地图移动的最高点用于边界控制**/
        private float LeftTopY = 0;

        public void setLayers(List<MapBaseLayer> layers) {
            this.layers.addAll(layers);
        }

        public void setSaveMatrix(Matrix saveMatrix) {
            this.saveMatrix.set(saveMatrix);
        }

        public void setCurrentMatrix(Matrix currentMatrix) {
            this.currentMatrix.set(currentMatrix);
        }

        public void setCurrentZoom(float currentZoom) {
            this.currentZoom = currentZoom;
        }

        public void setSaveZoom(float saveZoom) {
            this.saveZoom = saveZoom;
        }

        public void setCurrentRotateDegrees(float currentRotateDegrees) {
            this.currentRotateDegrees = currentRotateDegrees;
        }

        public void setOldDist(float oldDist) {
            this.oldDist = oldDist;
        }

        public void setLeftTopX(float leftTopX) {
            LeftTopX = leftTopX;
        }

        public void setLeftTopY(float leftTopY) {
            LeftTopY = leftTopY;
        }

        public void setStartTouch(PointF startTouch) {
            this.startTouch = startTouch;
        }

        public void setMid(PointF mid) {
            this.mid = mid;
        }

        public List<MapBaseLayer> getLayers() {
            return layers;
        }

        public Matrix getSaveMatrix() {
            return saveMatrix;
        }

        public Matrix getCurrentMatrix() {
            return currentMatrix;
        }

        public PointF getStartTouch() {
            return startTouch;
        }

        public PointF getMid() {
            return mid;
        }

        public float getCurrentZoom() {
            return currentZoom;
        }

        public float getSaveZoom() {
            return saveZoom;
        }

        public float getCurrentRotateDegrees() {
            return currentRotateDegrees;
        }

        public float getOldDist() {
            return oldDist;
        }

        public float getLeftTopX() {
            return LeftTopX;
        }

        public float getLeftTopY() {
            return LeftTopY;
        }
    }
}
