<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idMesin = isset($_POST['idMesin']) ? $_POST['idMesin'] : '';
    // $idMesin = "12C84B479B54";

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
            WHERE M.ID_Mesin = '$idMesin' AND M.Status = '2'";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $mnt = array();

    while ($row = mysqli_fetch_assoc($result)) {
        $mnt[] = $row;
    }

    $query = "SELECT M.*, L.Nama as Nama_Line, P.Nama as Nama_Proses
                FROM sis_ms_Mesin as M
                JOIN sis_ms_Line as L ON L.ID = M.ID_Line
                JOIN sis_ms_Proses as P ON P.ID = M.ID_Proses
                WHERE M.ID = '$idMesin'
                GROUP BY M.ID";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $mesin = mysqli_fetch_object($result);


    echo ($result) ?
        json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "maintenance" => $mnt, "mesin" => $mesin)) :
        json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
}
