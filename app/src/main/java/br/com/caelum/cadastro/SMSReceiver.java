package br.com.caelum.cadastro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by android5908 on 12/04/16.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");
        AlunoDAO dao = new AlunoDAO(context);
        SmsMessage sms[] = new SmsMessage[mensagens.length];
        String formato = (String) bundle.get("format");

        for (int n = 0; n < mensagens.length; n++){
            sms[n] = SmsMessage.createFromPdu((byte[]) mensagens[n],formato);
        }

        if (dao.isAluno(sms[0].getDisplayOriginatingAddress())){
            Toast.makeText(context,"SMS de aluno: " + sms[0].getMessageBody(),Toast.LENGTH_LONG).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
        dao.close();

    }
}
