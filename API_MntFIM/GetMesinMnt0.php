<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];
    // $id = "F29C4B718917";
    // $id = "12C84B479B54";
    $isError = false;

    $query = "SELECT M.*, L.Nama as Nama_Line, P.Nama as Nama_Proses
                FROM sis_ms_Mesin as M
                JOIN sis_ms_Line as L ON L.ID = M.ID_Line
                JOIN sis_ms_Proses as P ON P.ID = M.ID_Proses
                WHERE M.ID = '$id'";
    $result1 = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $mesin = mysqli_fetch_object($result1);

    if ($mesin != null) {
        $query = "SELECT M.*, 
                    E.NoMesin as NamaMesin, L.Nama as NamaLine,
                    P.Nama as NamaProses,
                    U.Nama as NamaPIC, 
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
                    WHERE M.ID_Mesin = '$id' AND M.Status = 0";
        $result2 = mysqli_query($conn, $query);
        $mnt = mysqli_fetch_object($result2);

        if ($mnt != null) {
            $isError = true;
        }
    }

    echo ($mesin != null) ?
        json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "isError" => $isError, "mesin" => $mesin, "maintenance" => $mnt)) :
        json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
}
