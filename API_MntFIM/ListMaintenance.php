<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $status = $_POST['status'];

    // $status = "1";
    // $id_user = "1";

    if (isset($_POST['id_user'])) {
        $id_user = $_POST['id_user'];
        $query = "SELECT M.*, 
                E.NoMesin as NamaMesin, E.ID_SeriesKontrol, L.Nama as NamaLine, L.ID as ID_Line,
                P.Nama as NamaProses,
                U.Nama as NamaPIC, U.ID as ID_PIC, 
                A.Masalah, A.Penyebab, A.Penanganan, S.Nama as NamaSparepart
                -- I.Nama as NamaShift
                FROM mnt_tr_Maintenance as M
                LEFT JOIN sis_ms_Mesin as E ON E.ID = M.ID_Mesin
                LEFT JOIN sis_ms_Proses as P ON P.ID = E.ID_Proses
                LEFT JOIN sis_ms_Line as L ON L.ID = E.ID_Line
                LEFT JOIN app_User as U ON U.ID = M.PIC
                LEFT JOIN mnt_ms_Masalah as A ON A.ID = M.ID_Masalah
                LEFT JOIN mnt_ms_Sparepart as S ON S.ID = M.ID_Sparepart
                -- LEFT JOIN M_Shift as I ON I.ID = M.ID_Shift
                WHERE M.Status = '$status' AND M.PIC = '$id_user'
                ORDER By NamaMesin ASC";

        $result = mysqli_query($conn, $query);
        // printf("Error: %s\n", mysqli_error($conn));
        $array = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $array[] = $row;
        }

        echo ($result) ?
            json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "result" => $array)) :
            json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
    } else {

        $query = "SELECT M.*, 
        E.NoMesin as NamaMesin, E.ID_SeriesKontrol, L.Nama as NamaLine, L.ID as ID_Line,
        P.Nama as NamaProses,
        U.Nama as NamaPIC, U.ID as ID_PIC, 
        A.Masalah, A.Penyebab, A.Penanganan, S.Nama as NamaSparepart
        -- I.Nama as NamaShift
        FROM mnt_tr_Maintenance as M
        LEFT JOIN sis_ms_Mesin as E ON E.ID = M.ID_Mesin
        LEFT JOIN sis_ms_Proses as P ON P.ID = E.ID_Proses
        LEFT JOIN sis_ms_Line as L ON L.ID = E.ID_Line
        LEFT JOIN app_User as U ON U.ID = M.PIC
        LEFT JOIN mnt_ms_Masalah as A ON A.ID = M.ID_Masalah
        LEFT JOIN mnt_ms_Sparepart as S ON S.ID = M.ID_Sparepart
        -- LEFT JOIN M_Shift as I ON I.ID = M.ID_Shift
        WHERE M.Status = '$status'
        ORDER By NamaMesin ASC";

        $result = mysqli_query($conn, $query);
        // printf("Error: %s\n", mysqli_error($conn));
        $array = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $array[] = $row;
        }

        echo ($result) ?
            json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "result" => $array)) :
            json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
    }
} else {

    $query = "SELECT M.*, 
                E.NoMesin as NamaMesin, E.ID_SeriesKontrol, L.Nama as NamaLine, L.ID as ID_Line,
                P.Nama as NamaProses,
                U.Nama as NamaPIC, U.ID as ID_PIC, 
                A.Masalah, A.Penyebab, A.Penanganan, S.Nama as NamaSparepart
                -- I.Nama as NamaShift
                FROM mnt_tr_Maintenance as M
                LEFT JOIN sis_ms_Mesin as E ON E.ID = M.ID_Mesin
                LEFT JOIN sis_ms_Proses as P ON P.ID = E.ID_Proses
                LEFT JOIN sis_ms_Line as L ON L.ID = E.ID_Line
                LEFT JOIN app_User as U ON U.ID = M.PIC
                LEFT JOIN mnt_ms_Masalah as A ON A.ID = M.ID_Masalah
                LEFT JOIN mnt_ms_Sparepart as S ON S.ID = M.ID_Sparepart
                -- LEFT JOIN M_Shift as I ON I.ID = M.ID_Shift
                ORDER By NamaMesin ASC";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $array = array();

    while ($row = mysqli_fetch_assoc($result)) {
        $array[] = $row;
    }

    echo ($result) ?
        json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "result" => $array)) :
        json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
}
