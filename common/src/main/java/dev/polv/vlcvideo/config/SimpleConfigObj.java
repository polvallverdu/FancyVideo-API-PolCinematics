/*
 * This file is part of the VLCVideoAPI.
 *
 * The VLCVideoAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The VLCVideoAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * The VLCVideoAPI uses VLCJ, Copyright 2009-2021 Caprica Software Limited,
 * licensed under the GNU General Public License.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCVideoAPI.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2024 <https://polv.dev/>
 */

package dev.polv.vlcvideo.config; //NOSONAR

import java.util.function.Predicate;

public class SimpleConfigObj {

    String key;
    String description;
    String range;

    Predicate<String> validator;

    public SimpleConfigObj(String key, String description, String range, Predicate<String> validator) {
        this.key = key;
        this.description = description;
        this.range = range;
        this.validator = validator;
    }

    public String toString(SimpleConfig config) {
        String value;
        if (validator.test(config.get(key))) {
            value = config.get(key);
        } else {
            value = config.defaultProperties.getProperty(key);
        }
        return "# " + description + "\n" + "# Range: " + range + "\n" + key + "=" + value + "\n";
    }
}
