package jp.co.casareal.activityresultapiforpermission;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    // 単一パーミッションの場合ActivityResultContractにRequestPermissionを指定
    private ActivityResultLauncher launcherSingle =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    Toast.makeText(this, "単一Permissionが取得できた", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "取得できなかった単一Permissionがある", Toast.LENGTH_SHORT).show();
                }
            });

    // 複数パーミッションの場合ActivityResultContractにRequestMultiplePermissionsを指定
    private ActivityResultLauncher launcherMulti =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), resultMap -> {
                AtomicBoolean flg = new AtomicBoolean(true);
                resultMap.values().forEach(
                        boolResult -> {
                            if (!boolResult) {
                                flg.set(false);
                            }
                        });

                if (flg.get()) {
                    Toast.makeText(this, "すべてのPermissionが取得できた", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "取得できなかったPermissionがある", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 取得したいパーミッションをlaunchの引数に渡す
        findViewById(R.id.btnSinglePermission).setOnClickListener(
                view -> {
                    launcherSingle.launch(Manifest.permission.CAMERA);
                });

        // 取得したいパーミッションも文字列配列をlaunchの引数に渡す
        findViewById(R.id.btnMultiPermission).setOnClickListener(
                view -> {
                    String[] permissions = {
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.READ_CALENDAR
                    };
                    launcherMulti.launch(permissions);
                });
    }
}