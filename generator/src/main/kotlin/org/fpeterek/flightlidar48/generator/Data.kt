package org.fpeterek.flightlidar48.generator

object Data {

    val airlines = mapOf(
        // Deutsche Lufthansa
        "LH" to listOf("D-HXRO", "D-QPWJ", "D-LHTH", "D-ECBD", "D-HJZJ", "D-BYHE", "D-QUPS", "D-GHZT", "D-ZUQX", "D-YVFR"),
        // Swissair
        "LX" to listOf("HB-YXK", "HB-ASF", "HB-OTF", "HB-ZNE", "HB-DVV", "HB-IWO", "HB-RAJ", "HB-HRS", "HB-HZF", "HB-CXB"),
        // Emirates
        "EK" to listOf("A6-VYM", "A6-GWF", "A6-YNI", "A6-BBA", "A6-FUG", "A6-ETT", "A6-HES", "A6-PPK", "A6-LKX", "A6-YPO"),
        // Delta Air Lines
        "DL" to listOf("N-XUCA", "N-QTUW", "N-DMPQ", "N-HNGX", "N-LCCI", "N-WFXT", "N-NLLR", "N-SHGX", "N-XSUR", "N-GOIM"),
        // American Airlines
        "AA" to listOf("N-QHFO", "N-IUUF", "N-SNMO", "N-TLNI", "N-CIFD", "N-DGOK", "N-TEOO", "N-FNXK", "N-ZANN", "N-GPUX"),
        // Air France
        "AF" to listOf("F-NYRH", "F-VCTC", "F-LBXF", "F-YWKV", "F-VRMN", "F-DKYF", "F-QJNZ", "F-ROLV", "F-CSFX", "F-DRRY"),
        // Air Canada
        "AC" to listOf("C-YTSD", "C-SKYK", "C-IUSH", "C-BHDP", "C-UNFG", "C-YTBW", "C-WSXE", "C-ZZQO", "C-JNLE", "C-HLOW"),
        // British Airways
        "BA" to listOf("G-JVPJ", "G-HHOH", "G-MYXS", "G-MWWN", "G-DWCF", "G-ZERJ", "G-ICZB", "G-IFEO", "G-KYXZ", "G-ZZDX"),
        // Czech Airlines
        "OK" to listOf("OK-YJI", "OK-MEH", "OK-RTE", "OK-PSH", "OK-OBV", "OK-XER", "OK-HHM", "OK-WOV", "OK-HSL", "OK-TUV"),
        // Smrtwings
        "QS" to listOf("OK-TXN", "OK-HZZ", "OK-QCP", "OK-QVG", "OK-RMT", "OK-KAB", "OK-RQW", "OK-HHQ", "OK-MUS", "OK-SUK"),
        // Singapore Airlines
        "SQ" to listOf("9V-JOZ", "9V-DTE", "9V-OOK", "9V-URH", "9V-SKH", "9V-XLH", "9V-CWU", "9V-ELE", "9V-HTP", "9V-FSH")
    )

    val airports = listOf(
        "KLAX", "KJFK", "KATL", "CYYZ", "EDDF", "EDDM", "EGLL", "LFPG", "LFBO", "LSZH", "LKPR", "LKMT", "LKXB", "WSSS", "OMDB"
    )

}
