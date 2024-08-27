<?php
require_once 'Connection.php';


date_default_timezone_set("Asia/Jakarta");
$now = date("Y-m-d H:i:s");
$now = strtotime($now - 7);
$now = date('Y-m-d H:i:s', $now);
// echo $now;

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
            WHERE M.Status = '0' AND M.TimeError = '$now'
            ORDER By NamaMesin ASC";

$result2 = mysqli_query($conn, $query);
// printf("Error: %s\n", mysqli_error($conn));
$mnt = mysqli_fetch_object($result2);
echo json_encode($mnt);
