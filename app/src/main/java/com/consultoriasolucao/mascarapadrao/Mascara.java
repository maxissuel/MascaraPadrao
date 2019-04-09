package com.consultoriasolucao.mascarapadrao;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mascara {
    public static final String CPF = "###.###.###-##";
    public static final String CNPJ = "##.###.###/####-##";
    public static final String TELEFONE = "(##) ####-####";
    public static final String CELULAR = "(##) # ####-####";
    public static final String CEP = "#####-###";

    public static String retiraMascara(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll(" ", "")
                .replaceAll(",", "");
    }

    public static String aplicaMascara(String mask, String text) {
        int i = 0;
        String mascara = "";
        for (char m : mask.toCharArray()) {
            if (m != '#') {
                mascara += m;
                continue;
            }
            try {
                mascara += text.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }

        return mascara;
    }

    public static TextWatcher insert(final String maskPadrao, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String maskAtual = maskPadrao;
                if(maskAtual.equals(CPF) || maskAtual.equals(CNPJ)){
                    String str = Mascara.retiraMascara(s.toString());
                    if(str.length() <= 11)
                        maskAtual = CPF;
                    else
                        maskAtual = CNPJ;
                }

                String str = Mascara.retiraMascara(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : maskAtual.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }
}
