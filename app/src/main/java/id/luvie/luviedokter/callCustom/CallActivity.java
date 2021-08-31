package id.luvie.luviedokter.callCustom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.calls.api.MesiboCallActivity;

import id.luvie.luviedokter.R;

public class CallActivity extends MesiboCallActivity {

    private boolean mInit = false;
    MesiboCall.Call mCall = null;
    MesiboCall.VideoProperties mVideo = null;

    class DemoUser {
        public String token;
        public String address;

        DemoUser(String token, String address) {
            this.token = token;
            this.address = address;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_call);



        int res = checkPermissions(true);

        /* permissions were declined */
        if (res < 0) {
            finish();
            return;
        }

        /* all permissions were already granted */
        if (0 == res) {
            initCall();
        } else {
            /* permission requested - wait for results */
            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initCall();
        } else
            finish();
    }

    private void initCall() {
        if (mInit) return;
        mInit = true;

        mCall = MesiboCall.getInstance().getActiveCall();

        if (null == mCall) {

            Mesibo.UserProfile profile = new Mesibo.UserProfile();

            profile.address = "Terry";

            MesiboCall.CallProperties cc = MesiboCall.getInstance().new CallProperties(mCall.isVideoCall());
            cc.parent = this;
            cc.activity = this;
            cc.user = profile;
            cc.video.source = mCall.getVideoSource();

            MesiboCallUi.Listener cul = MesiboCallUi.getInstance().getListener();
            if (null != cul)
                cul.MesiboCallUi_OnConfig(cc);

            mCall = MesiboCall.getInstance().call(cc);

            if (null == mCall || !mCall.isCallInProgress()) {
                finish();
                return;
            }
        }

        super.initCall(mCall);

        CallFragment fragment = null;
        fragment = new CallFragment();

        /* OPTIONAL - you can use different fragments for different type of calls */
        if (mCall.isVideoCall()) {
            // show video fragment
        } else if (mCall.isIncoming() && mCall.isCallInProgress() && !mCall.isAnswered()) {
            //show incoming audio fragment
        } else {
            //show outgoing audio fragemnt
        }

        fragment.MesiboCall_OnSetCall(this, mCall);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.top_fragment_container, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mInit)
            return;

        mCall = MesiboCall.getInstance().getActiveCall();
        if (null == mCall) {
            finish();
            return;
        }
    }

}