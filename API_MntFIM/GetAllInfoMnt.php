<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idMnt = isset($_POST['idMnt']) ? $_POST['idMnt'] : '';
    // $idMnt = "13";

    $query = "SELECT M.*, 
            E.NoMesin as NamaMesin, E.ID_SeriesKontrol, L.Nama as NamaLine,
            Pr.Nama as NamaProses,
            U.Nama as NamaPIC, 
            Ma.Masalah, Ma.Penyebab, Ma.Penanganan, S.Nama as NamaSparepart,
            B.Nama as NamaBagianMesin, P.Penyebab as tekPenyebab, P.Perbaikan as tekPerbaikan,
            D.Definisi as tekDefinisi, A.Display as tekDisplayAlarm, A.Nama as tekNamaAlarm,
            SK.NamaSeries as tekSeries, JK.NamaJenis as tekJenis
            FROM mnt_tr_Maintenance as M
            LEFT JOIN app_User as U ON U.ID = M.PIC
            LEFT JOIN mnt_ms_Masalah as Ma ON Ma.ID = M.ID_Masalah
            LEFT JOIN sis_ms_Mesin as E ON E.ID = M.ID_Mesin
                LEFT JOIN sis_ms_Proses as Pr ON Pr.ID = E.ID_Proses
                LEFT JOIN sis_ms_Line as L ON L.ID = E.ID_Line
            LEFT JOIN mnt_ms_Sparepart as S ON S.ID = M.ID_Sparepart
                LEFT JOIN mnt_ms_BagianMesin as B ON B.ID = S.ID_BagianMesin
            LEFT JOIN mnt_md_Penyebab as P ON P.ID = M.ID_PenyebabAlarm
                LEFT JOIN mnt_md_Definisi as D ON D.ID = P.ID_Definisi
                    LEFT JOIN mnt_md_Alarm as A ON A.ID = D.ID_Alarm
                        LEFT JOIN mnt_md_SeriesKontrol as SK ON SK.ID = A.ID_SeriesKontrol
                            LEFT JOIN mnt_md_JenisKontrol as JK ON JK.ID = SK.ID_JenisKontrol
            WHERE M.ID = '$idMnt'";
    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $mnt = mysqli_fetch_object($result);

    $query2 = "SELECT M.*, L.Nama as Nama_Line, P.Nama as Nama_Proses, P.Nama as Nama_Proses, S.NamaSeries, J.NamaJenis
                FROM mnt_tr_Maintenance as N
                LEFT JOIN sis_ms_Mesin as M ON M.ID = N.ID_Mesin
                LEFT JOIN sis_ms_Line as L ON L.ID = M.ID_Line
                LEFT JOIN sis_ms_Proses as P ON P.ID = M.ID_Proses
                LEFT JOIN mnt_md_SeriesKontrol as S ON S.ID = M.ID_SeriesKontrol
                    LEFT JOIN mnt_md_JenisKontrol as J ON J.ID = S.ID_JenisKontrol
                WHERE N.ID = '$idMnt'
                GROUP BY M.ID";
    $result2 = mysqli_query($conn, $query2);
    $mesin = mysqli_fetch_object($result2);

    $query3 = "SELECT D.*
                FROM mnt_tr_MaintenanceDetail as D
                WHERE D.ID_Maintenance = '$idMnt'";
    $result3 = mysqli_query($conn, $query3);
    $listDetail = array();

    while ($row = mysqli_fetch_assoc($result3)) {
        $listDetail[] = $row;
    }

    echo ($result) ?
        json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "maintenance" => $mnt, "mesin" => $mesin, "detailMnt" => $listDetail)) :
        json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
}
