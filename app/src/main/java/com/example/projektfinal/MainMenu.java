package com.example.projektfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

//    public static ArrayList<Fixture> fixtureList;
    public static int fixtureQuantity = 0;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_menu);


        //Tu ogarnac dialog z formsem do tworzenia, potem sie ogarnie jak to polaczyc w kupe
//        final Button btnAddFixture = (Button) findViewById(R.id.addFixture);
        builder = new AlertDialog.Builder(this);



    }

    /*** TODO
     * 1. Dodać button który będzie dodawał urządzenie
     * (po naciśnięciu tego buttona będzie tworzony fragment (?) z mini formularzem)
     * Potem append do arrayListy i refresh
     *
     * Napisać GUI które będzie wyświetlać tę arrayListę
     * Może dodać pola x,y które będą wskazywać pozycję na gridzie, tak
     * żeby nie bawić się w ręczne ustawianie tego
     *
     * Color picker chyba bedzie zbyt uposledzony do zrobienia ZAMIAST tego
     * wysylanie pliku po prostym FTP
     *
     * W liscie urzadzenia zrobic pop up ktory bedzie wyswietlal switche
     *
     * W fixture builderze zrobic prosta apke w javie ktora bedzie odbierac
     * pliczek, i by go kopiowala do sciezki avolite'a . Chyba ze zmienic koncepcję
     * i całkiem rozbudować tego buildera, ale to juz chyba bedzie za trudne
     *
     * Zrobic jeszcze przeglądarkę PDF, notatnik, czat
     * (może zamiast FTP wysyłkę załączników) i powinno wystarczyć.
     *
     * Deadline'y:
     * -PUM: 15.01
     * -WSIZI: tak
     * -IO: 31.01
     * -PII: 16.01
     */

    public void addToList(){
        Fixture addFixture = new Fixture(
                "Flash LED", 123, "5CH"
        );
    }




}