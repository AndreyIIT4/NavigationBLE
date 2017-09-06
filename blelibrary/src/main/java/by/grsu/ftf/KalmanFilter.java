package by.grsu.ftf;

/**
 * Created by Lenovo on 29.08.2017.
 */

public class KalmanFilter {
    private float R = 1.0f;
    private float Q = 1.0f;
    private float A = 1.0f;
    private float B = 0.0f;
    private float C = 1.0f;
    private float cov = Float.NaN;
    private float x = Float.NaN;
    public KalmanFilter(float r, float q) {
        R = r;
        Q = q;
    }
    public float filter(float z) {
        return filter(z, 0.0f);
    }
    public float filter(float z, float u) {
        if (Float.isNaN(this.x)) {
            this.x = (1 / this.C) * z;
            this.cov = (1 / this.C) * this.Q * (1 / this.C);
        } else {
            // Compute prediction
            float predX = (this.A * this.x) + (this.B * u);
            float predCov = ((this.A * this.cov) * this.A) + this.R;
            // Kalman gain
            float K = predCov * this.C * (1 / ((this.C * predCov * this.C) + this.Q));
            // Correction
            this.x = predX + K * (z - (this.C * predX));
            this.cov = predCov - (K * this.C * predCov);
        }
        return this.x;
    }
    public float lastMeasurement() {
        return this.x;
    }
    public void setMeasurementNoise(float noise) {
        this.Q = noise;
    }
    public void setProcessNoise(float noise) {
        this.R = noise;
    }
}
