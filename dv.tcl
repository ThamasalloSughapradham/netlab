set ns [new Simulator]
$ns rtproto DV

set nf [open dv1.tr w]
$ns trace-all $nf

set nr [open dv2.nam w]
$ns namtrace-all $nr

proc finish {} {
    global ns nf nr
    $ns flush-trace
    close $nf
    close $nr
    exec nam dv2.nam
    exit 0
}

for {set i 0} {$i<12} {incr i} {
    set n$i [$ns node]
}

set linkParams "1Mb 10ms DropTail"
$ns duplex-link $n0 $n1 $linkParams
$ns duplex-link $n1 $n2 $linkParams
$ns duplex-link $n2 $n3 $linkParams
$ns duplex-link $n3 $n4 $linkParams
$ns duplex-link $n4 $n5 $linkParams
$ns duplex-link $n5 $n6 $linkParams
$ns duplex-link $n6 $n7 $linkParams
$ns duplex-link $n7 $n8 $linkParams
$ns duplex-link $n8 $n0 $linkParams
$ns duplex-link $n0 $n9 $linkParams
$ns duplex-link $n1 $n10 $linkParams
$ns duplex-link $n9 $n11 $linkParams
$ns duplex-link $n10 $n11 $linkParams
$ns duplex-link $n11 $n5 $linkParams

set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005

set null0 [new Agent/Null]
$ns attach-agent $n5 $null0

set udp1 [new Agent/UDP]
$ns attach-agent $n1 $udp1
set cbr1 [new Application/Traffic/CBR]
$cbr1 attach-agent $udp1
$cbr1 set packetSize_ 500
$cbr1 set interval_ 0.005

set null1 [new Agent/Null]
$ns attach-agent $n5 $null1

$ns connect $udp0 $null0
$ns connect $udp1 $null1

$ns rtmodel-at 10.0 down $n11 $n5
$ns rtmodel-at 30.0 up $n11 $n5
$ns rtmodel-at 15.0 down $n7 $n6
$ns rtmodel-at 20.0 up $n7 $n6

$ns at 0.1 "$cbr1 start"
$ns at 0.2 "$cbr0 start"
$ns at 45.0 "$cbr1 stop"
$ns at 45.1 "$cbr0 stop"
$ns at 50.0 "finish"

$ns run