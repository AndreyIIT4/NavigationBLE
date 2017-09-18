package by.grsu.ftf.Calculations;

import android.graphics.PointF;

/**
 * Created by Lenovo on 29.08.2017.
 */

public class Trilaterus {
    public static PointF trilaterate(PointF a, PointF b, PointF c, float distA, float distB, float distC) {
        float P1[] = { a.x, a.y, 0 };
        float P2[] = { b.x, b.y, 0 };
        float P3[] = { c.x, c.y, 0 };
        // ex = (P2 - P1)/(numpy.linalg.norm(P2 - P1))
        float ex[] = { 0, 0, 0 };
        float P2P1 = 0;
        for (int i = 0; i < 3; i++) {
            P2P1 += Math.pow(P2[i] - P1[i], 2);
        }
        for (int i = 0; i < 3; i++) {
            ex[i] = (float) ((P2[i] - P1[i]) / Math.sqrt(P2P1));
        }
        // i = dot(ex, P3 - P1)
        float p3p1[] = { 0, 0, 0 };
        for (int i = 0; i < 3; i++) {
            p3p1[i] = P3[i] - P1[i];
        }
        float ivar = 0;
        for (int i = 0; i < 3; i++) {
            ivar += (ex[i] * p3p1[i]);
        }
        // ey = (P3 - P1 - i*ex)/(numpy.linalg.norm(P3 - P1 - i*ex))
        float p3p1i = 0;
        for (int  i = 0; i < 3; i++) {
            p3p1i += Math.pow(P3[i] - P1[i] - ex[i] * ivar, 2);
        }
        float ey[] = { 0, 0, 0};
        for (int i = 0; i < 3; i++) {
            ey[i] = (float) ((P3[i] - P1[i] - ex[i] * ivar) / Math.sqrt(p3p1i));
        }
        // ez = numpy.cross(ex,ey)
        // if 2-dimensional vector then ez = 0
        float ez[] = { 0, 0, 0 };
        // d = numpy.linalg.norm(P2 - P1)
        float d = (float) Math.sqrt(P2P1);
        // j = dot(ey, P3 - P1)
        float jvar = 0;
        for (int i = 0; i < 3; i++) {
            jvar += (ey[i] * p3p1[i]);
        }
        // from wikipedia
        // plug and chug using above values
        float x = (float) ((Math.pow(distA, 2) - Math.pow(distB, 2) + Math.pow(d, 2)) / (2 * d));
        float y = (float) (((Math.pow(distA, 2) - Math.pow(distC, 2) + Math.pow(ivar, 2)
                + Math.pow(jvar, 2)) / (2 * jvar)) - ((ivar / jvar) * x));
        // only one case shown here
        float z = (float) Math.sqrt(Math.pow(distA, 2) - Math.pow(x, 2) - Math.pow(y, 2));
        if (Float.isNaN(z)) z = 0;
        // triPt is an array with ECEF x,y,z of trilateration point
        // triPt = P1 + x*ex + y*ey + z*ez
        float triPt[] = { 0, 0, 0 };
        for (int i = 0; i < 3; i++) {
            triPt[i] =  P1[i] + ex[i] * x + ey[i] * y + ez[i] * z;
        }
        // convert back to lat/long from ECEF
        // convert to degrees
        float lon = triPt[0];
        float lat = triPt[1];
        return new PointF(lon, lat);
    }
}
