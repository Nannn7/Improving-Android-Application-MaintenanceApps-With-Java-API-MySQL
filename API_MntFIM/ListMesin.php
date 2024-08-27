<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idLine = isset($_POST['idLine']) ? $_POST['idLine'] : '';
    // $idLine = $_POST['idLine'];
    // printf("idLine: %s\n", $idLine);
    // $idLine = "1";

    $query = "SELECT M.*, L.Nama as Nama_Line, P.Nama as Nama_Proses, S.NamaSeries, J.NamaJenis
            FROM sis_ms_Mesin as M
            LEFT JOIN sis_ms_Line as L ON L.ID = M.ID_Line
            LEFT JOIN sis_ms_Proses as P ON P.ID = M.ID_Proses
            LEFT JOIN mnt_md_SeriesKontrol as S ON S.ID = M.ID_SeriesKontrol
                LEFT JOIN mnt_md_JenisKontrol as J ON J.ID = S.ID_JenisKontrol
            WHERE M.ID_Line = '$idLine'
            GROUP BY M.NoMesin
            ORDER BY M.NoMesin";

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
