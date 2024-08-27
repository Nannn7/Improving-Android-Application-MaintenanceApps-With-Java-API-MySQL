<?php
require_once 'Connection.php';

$query = "SELECT J.ID as ID_JenisKontrol, J.NamaJenis, S.ID as ID_SeriesKontrol, S.NamaSeries
            FROM mnt_md_JenisKontrol as J
            JOIN mnt_md_SeriesKontrol as S ON S.ID_JenisKontrol = J.ID
            ORDER By NamaJenis ASC, NamaSeries ASC";

$result = mysqli_query($conn, $query);
// printf("Error: %s\n", mysqli_error($conn));
$array = array();

while ($row = mysqli_fetch_assoc($result)) {
    $array[] = $row;
}

echo ($result) ?
    json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "result" => $array)) :
    json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
