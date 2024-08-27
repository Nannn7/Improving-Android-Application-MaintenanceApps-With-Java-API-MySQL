<?php
require_once 'Connection.php';

function imageResize($imageResourceId, $width, $height)
{
    $targetWidth = $width / 3;
    $targetHeight = $height / 3;

    $targetLayer = imagecreatetruecolor($targetWidth, $targetHeight);
    imagecopyresampled($targetLayer, $imageResourceId, 0, 0, 0, 0, $targetWidth, $targetHeight, $width, $height);

    return $targetLayer;
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $file_name = $_FILES['myFile']['name'];
    $file_size = $_FILES['myFile']['size'];
    $file_type = $_FILES['myFile']['type'];
    $temp_name = $_FILES['myFile']['tmp_name'];

    // $file_name = substr($file_name, 0, 42);

    if ($file_size != 0) {
        // benahi path
        // $location = "C:/xampp\htdocs\FIM_Maintenance\Image\Maintenance/";

        // $save = move_uploaded_file($temp_name, $location . $file_name);

        // $fileResize = $location . $file_name;
        // $image = imagecreatefromjpeg($fileResize);
        // $imgResized = imagescale($image, 800, 600);


        $sourceProperties = getimagesize($temp_name);
        // $fileNewName = time();
        $folderPath = "C:/xampp\htdocs\FIM_Maintenance\Image\Maintenance/";
        // $ext = pathinfo($file_name, PATHINFO_EXTENSION);
        $imageType = $sourceProperties[2];
        $save;

        switch ($imageType) {
            case IMAGETYPE_PNG:
                $imageResourceId = imagecreatefrompng($temp_name);
                $targetLayer = imageResize($imageResourceId, $sourceProperties[0], $sourceProperties[1]);
                $save = imagepng($targetLayer, $folderPath . $file_name);
                break;
            case IMAGETYPE_GIF:
                $imageResourceId = imagecreatefromgif($temp_name);
                $targetLayer = imageResize($imageResourceId, $sourceProperties[0], $sourceProperties[1]);
                $save = imagegif($targetLayer, $folderPath . $file_name);
                break;
            case IMAGETYPE_JPEG:
                $imageResourceId = imagecreatefromjpeg($temp_name);
                $targetLayer = imageResize($imageResourceId, $sourceProperties[0], $sourceProperties[1]);
                $save = imagejpeg($targetLayer, $folderPath . $file_name);
                break;
            default:
                echo json_encode("Invalid Image type.");
                exit;
                break;
        }

        // move_uploaded_file($file, $folderPath . $file_name . "." . $ext);

        if ($save) {
            $arr = explode("_", $file_name, 2);
            $idMnt = $arr[1];

            $query = "INSERT INTO mnt_tr_MaintenanceDetail (ID_Maintenance, Foto) VALUES ('$idMnt', '$file_name')";

            $result1 = mysqli_query($conn, $query);
            // printf("Error: %s\n", mysqli_error($conn));

            echo ($result1) ?
                json_encode(array("kode" => 1, "pesan" => "Data berhasil input filesize: " . $file_size)) :
                json_encode(array("kode" => 0, "pesan" => "Data gagal input di: " . $location . $file_name));
        } else {
            echo json_encode("tidak ke save");
        }
    } else {
        echo json_encode("file tidak disimpan karena kosong");
    }
}
