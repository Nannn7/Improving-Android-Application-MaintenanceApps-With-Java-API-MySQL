<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];

    // $id = "1";

    $query = "SELECT M.*, 
            E.NoMesin as NamaMesin, L.Nama as NamaLine, L.ID as ID_Line,
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
            WHERE M.ID = '$id'";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $row = mysqli_fetch_object($result);

    echo json_encode($row);
}
