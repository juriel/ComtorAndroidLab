package net.comtor.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.LinkedList;
import java.util.StringJoiner;

import androidx.core.content.ContextCompat;

public class PermissionHelper {
    static String[] permissions = new String[]
            {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION

            };


    public static String currentPermissionsStr(Context ctx) {
        LinkedList<String> l = currentPermissions(ctx);
        StringJoiner sj = new StringJoiner(",");
        l.forEach(s -> sj.add(s));
        return sj.toString();

    }

    public static LinkedList<String> currentPermissions(Context ctx) {
        LinkedList<String> resp = new LinkedList<>();
        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(ctx, p);
            if (result == PackageManager.PERMISSION_GRANTED) {
                resp.add(p);
            }
        }
        return resp;
    }

    public static String getFirstNonGrantedPermission(Context ctx) {
        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(ctx, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return p;
            }
        }
        return null;
    }
}
