open (INPUT,"<","M1.dat");

while(my $line = <INPUT>){
$count = 0;
	$count++ while($line =~ m/	/g);
	print "$count\n"


}