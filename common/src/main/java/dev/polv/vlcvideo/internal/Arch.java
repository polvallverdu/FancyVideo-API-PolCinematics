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

package dev.polv.vlcvideo.internal; //NOSONAR

import com.sun.jna.Platform;

public enum Arch {
    x86,
    amd64,
    arm,
    arm64,
    strange,
    unknown;

    private static Arch arch;

    public static Arch getArch() {
        if (arch == null) {
            switch (Platform.ARCH) {
                case ("x86") -> arch = x86;
                case ("amd64"), ("x86-64") -> arch = amd64;
                case ("ppc"), ("ppc64"), ("ppc64le"), ("ia64"), ("sparcv9"), ("mips64"), ("mips64el") -> arch = strange;
                case ("arm"), ("armel") -> arch = arm;
                case ("arm64") -> arch = arm64;
                default -> arch = unknown;
            }
        }
        return arch;
    }
}
