/*
 * Copyright (C) 2021 Eugenio Marconicchio <eugeniomarconicchio@protonmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.bunnken.colortester;

import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Main view configurations and functions
 * @author Eugenio Marconicchio <eugeniomarconicchio@protonmail.com>
 */
public class MainController {
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField textField;
    @FXML
    private Label label;

    private void setStyle(String color) {
        gridPane.setStyle("-fx-background-color: " + color);
    }
    
    private void toHex(int r, int g, int b) {
        String hex = String.format("#%02x%02x%02x", r, g, b);
        label.setText("Hexadecimal: " + hex);
    }
    
    private void toRgb(String hex) {
        int r;
        int g;
        int b;
        
        /**
         * In this case we know it's a 3 values code so we duplicate the single
         * values
         */
        if (hex.length() == 4) {
            char[] chars = hex.toCharArray();
            hex = String.format(
                "#%c%c%c%c%c%c",
                chars[1], chars[1],
                chars[2], chars[2],
                chars[3], chars[3]
            );
        }
        
        /* We take every 2 values and convert them to integers in base 16 */
        r = Integer.parseInt(hex.substring(1, 3), 16);
        g = Integer.parseInt(hex.substring(3, 5), 16);
        b = Integer.parseInt(hex.substring(5, 7), 16);
        
        label.setText(String.format("RGB: %d %d %d", r, g, b));
    }
    
    private boolean parseHex(String code) {
        if (Pattern.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", code)) {
            toRgb(code);
            setStyle(code + ";");
            return true;
        }
        return false;
    }
    
    private boolean parseRgb(String code) {
        String[] pieces = code.split(" ");
        if (pieces.length == 3) {
            int partOne;
            int partTwo;
            int partThree;
            
            try {
                partOne = Integer.parseInt(pieces[0]);
                partTwo = Integer.parseInt(pieces[1]);
                partThree = Integer.parseInt(pieces[2]);
            } catch (NumberFormatException e) {
                return false;
            }

            if (partOne >= 0 && partOne < 256 &&
                partTwo >= 0 && partTwo < 256 &&
                partThree >= 0 && partThree < 256) {
                toHex(partOne, partTwo, partThree);
                setStyle(String.format("rgb(%d, %d, %d);", partOne, partTwo,
                    partThree));
                return true;
            }
        }
        return false;
    }
    
    private boolean checkCode(String code) {
        if (!code.isEmpty()) {
            if (parseHex(code)) return true;
            else if (parseRgb(code)) return true;
        }
        return false;
    }
    
    @FXML
    private void getCode(ActionEvent event) {
        String code = textField.getText();
        if (!checkCode(code))
            label.setText("Enter a valid hexadecimal or rgb color!");
    }
}
