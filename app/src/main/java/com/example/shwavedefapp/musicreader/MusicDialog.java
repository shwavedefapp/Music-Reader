package com.example.shwavedefapp.musicreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

class MusicDialog {

    private MainActivity m;
    Uri fileUri;
    Button browseFile;

    private static final LayoutParams margins = new LayoutParams
            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static final int READ_REQUEST_CODE = 1;
    public static final String EXTRA_URI = "com.example.shwavedefapp.musicreader.FILEURI";

    MusicDialog(MainActivity call) {m = call;}

    void dialogSetup() {
        final AlertDialog dialog = new AlertDialog.Builder(m)
                .setTitle(m.getString(R.string.dialog_title))
                .setView(R.layout.addsheet_dialog)
                .setPositiveButton(m.getString(R.string.dialog_create),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onDialogCreateNewSheet();
                            }
                        })
                .setNegativeButton(m.getString(R.string.dialog_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).create();
        dialog.show();

        browseFile = (Button) dialog.findViewById(R.id.dialog_browse);
        browseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogBrowse();
            }
        });
    }

    // TODO (1) -- CREATE SEPARATE CLASS FOR SPECIAL BUTTON
    private void onDialogCreateNewSheet() {
        if(fileUri == null) {
            Toast.makeText(m, "No file selected", Toast.LENGTH_SHORT).show();
        } else {
            Intent newPdf = new Intent(m, PDFActivity.class);
            newPdf.putExtra(EXTRA_URI, fileUri);
            m.startActivity(newPdf);
            Button newSheet = new Button(m);
            newSheet.setBackgroundColor(ContextCompat.getColor(m, R.color.colorPrimary));
            margins.setMargins(0, 0, 0, 16);
            newSheet.setLayoutParams(margins);
            m.mListSheets.addView(newSheet);
            if (m.mListSheets.getChildCount() == 2) m.mNoMusicHint.setVisibility(View.GONE);
        }
    }

    private void onDialogBrowse() {
        Intent open = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        open.addCategory(Intent.CATEGORY_OPENABLE);
        open.setType("application/pdf");
        m.startActivityForResult(open, READ_REQUEST_CODE);
    }
}
