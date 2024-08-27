<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $data = (array)json_decode(file_get_contents('php://input'));

    $ID_Maintenance = $data['ID_Maintenance'];
    $Foto = $data['Foto'];

    $query = "INSERT INTO mnt_tr_MaintenanceDetail (ID_Maintenance, Foto)
                VALUES ('$ID_Maintenance', '$Foto')";

    $result1 = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));

    echo ($result1) ?
        json_encode(array("kode" => 1, "pesan" => "Data berhasil ditambahkan")) :
        json_encode(array("kode" => 0, "pesan" => "Data gagal ditambahkan"));
}
