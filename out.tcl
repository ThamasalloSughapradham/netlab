set ns [new Simulator]

set nf [open trace1.tr w]
$ns trace-all $nf

set nr [open out1.nam w]
$ns namtrace-all $nr

proc finish {} {
    global ns nf nr
    $ns flush-trace
    close $nf
    close $nr
    exec nam out1.nam
    exit 0
}

for {set i 0} {$i<4} {incr i} {
    set n$i [$ns node]
}

$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail
$ns duplex-link $n2 $n3 3Mb 10ms DropTail

set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0
set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

set tcp1 [new Agent/TCP]
$ns attach-agent $n1 $tcp1
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1

set tcpsink0 [new Agent/TCPSink]
$ns attach-agent $n3 $tcpsink0
set tcpsink1 [new Agent/TCPSink]
$ns attach-agent $n3 $tcpsink1

$ns connect $tcp0 $tcpsink0
$ns connect $tcp1 $tcpsink1

$ns at 1.0 "$ftp0 start"
$ns at 1.1 "$ftp1 start"
$ns at 10.0 "$ftp0 stop"
$ns at 10.1 "$ftp1 stop"
$ns at 10.2 "finish"

$ns run