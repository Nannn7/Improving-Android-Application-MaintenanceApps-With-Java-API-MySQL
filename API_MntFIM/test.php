<?php
// echo date_default_timezone_get();
date_default_timezone_set("Asia/Jakarta");
echo "Today is " . date("Y-m-d H:i:s") . "<br><br>";
echo strtotime(date("Y-m-d H:i:s")) . "<br><br>";
echo strtotime(date("2021-07-27 09:41:38")) . "<br><br>";
echo strtotime(date("2021-07-27 09:41:51")) . "<br><br>";
echo strtotime(date("2021-07-27 09:41:51")) - strtotime(date("2021-07-27 09:41:38")) . "<br><br>";

// date_default_timezone_set("Asia/Jakarta");
// $time = DateTime::createFromFormat('Y-m-d H:i:s', '2021-06-29 15:22:58');
// echo $time->format("j M Y | H:i:s")
// $file_name = "MNT_13_20210408_150323_8627827484856431437.jpg";
// $file_name = substr($file_name, 0, 42);
// echo $file_name;
// echo "http://localhost/FIM_Maintenance/image/Alarm_Definisi/FRywV8pzi4BfR3Orzqhs.jpg";


// $location = "http://localhost/FIM_Maintenance/image/";
// $location = "C:/xampp/htdocs/FIM_Maintenance/Image/";
// $file_name = "PC.png";
// $fileResize = $location . $file_name;

// $newname = "PC2.png";
// $image = imagecreatefromstring(file_get_contents($location . $file_name));
// $imgResized = imagescale($image, 800, 600);

// $save = move_uploaded_file($imgResized, $location . $newname);
// if ($save) echo "success";
// else echo "failed";

// $test = "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";


// $folderUpload = "D:/00Magang/";
// $image = str_replace('data:image/png;base64,', '', $test);
// $image = str_replace(' ', '+', $image);
// $file = $folderUpload . "MyImage" . '.jpg';
// $data = base64_decode($test, true);

// echo $data;

// echo file_put_contents($file, $data);;
?>


<!-- <!DOCTYPE>
<html>

<head>
    <title></title>
</head>

<body>
    <img src="<?php // echo "http://localhost/FIM_Maintenance/image/Alarm_Definisi/FRywV8pzi4BfR3Orzqhs.jpg" 
                ?>">


<div class="container">
    <form action="testUpload.php" method="post" enctype="multipart/form-data">
        <input type="file" name="image" />
        <input type="submit" name="submit" value="Submit" />
    </form>
</div>
</body>

</html> -->