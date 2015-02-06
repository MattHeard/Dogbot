#!/usr/bin/python3

import io
import sys
from time import sleep

def sanitise(in_stream=sys.stdin, out_stream=sys.stdout):
        in_stream.seek(0)
        while True:
                ch = in_stream.read(1)
                if not ch:
                        break
                if ch.isalnum():
                        print(ch.upper(), end='', file=out_stream)
                if ch.isspace():
                        print(' ', end='', file=out_stream)

def convert_to_morse(in_stream=sys.stdin, out_stream=sys.stdout):
        d = { 'A': '.-', 'B': '-...', 'C': '-.-.', 'D': '-..', 'E': '.',
                        'F': '..-.', 'G': '--.', 'H': '....', 'I': '..',
                        'J': '.---', 'K': '-.-', 'L': '.-..', 'M': '--',
                        'N': '-.', 'O': '---', 'P': '.--.', 'Q': '--.-',
                        'R': '.-.', 'S': '...', 'T': '-', 'U': '..-',
                        'V': '...-', 'W': '.--', 'X': '-..-', 'Y': '-.--',
                        'Z': '--..', '1': '.----', '2': '..---', '3': '...--',
                        '4': '....-', '5': '.....', '6': '-....', '7': '--...',
                        '8': '---..', '9': '----.', '0': '-----' }
        in_stream.seek(0)
        while True:
                ch = in_stream.read(1)
                if not ch:
                        break
                if ch.isalnum():
                        print(d[ch], end=' ', file=out_stream)
                if ch.isspace():
                        print('/', end=' ', file=out_stream)

def convert_to_bits(in_stream=sys.stdin, out_stream=sys.stdout):
        d = { '.': '10', '-': '1110', ' ': '00', '/': '000' }
        in_stream.seek(0)
        while True:
                ch = in_stream.read(1)
                if not ch:
                        break
                if ch in list(d.keys()):
                        print(d[ch], end='', file=out_stream)

def turn_led_on():
        pass

def turn_led_off():
        pass

def flash_led(in_stream=sys.stdin, period=1):
        while True:
                ch = in_stream.read(1)
                if not ch:
                        break
                if ch == '1':
                        turn_led_on()
                        print(1)
                        sleep(period)
                if ch == '0':
                        turn_led_off()
                        print(0)
                        sleep(period)

if __name__ == "__main__":
        arg_len = len(sys.argv)
        in_stream = sys.stdin
        out_stream = sys.stdout
        if arg_len >= 2:
                in_stream_name = sys.argv[1]
                in_stream = open(in_stream_name, 'r', encoding='utf-8')
                if arg_len == 3:
                        out_stream_name = sys.argv[2]
                        out_stream = open(out_stream_name, 'w', encoding='utf-8')
        sanitised_stream = io.StringIO()
        sanitise(in_stream, sanitised_stream)
        morse_stream = io.StringIO()
        convert_to_morse(sanitised_stream, morse_stream)
        bit_stream = io.StringIO()
        convert_to_bits(morse_stream, bit_stream)
        flash_led(bit_stream)
