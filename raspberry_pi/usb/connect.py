#! /usr/bin/python3

import struct
import sys
import usb

SAMSUNG_VENDOR_ID = 0x04e8
GALAXY_S4_PRODUCT_ID = 0x6860

def findDevice():
    dev = usb.core.find(
            idVendor=SAMSUNG_VENDOR_ID,
            idProduct=GALAXY_S4_PRODUCT_ID)
    if dev is None:
        print('Face was not found. Exiting.')
        sys.exit()
    else:
        print('The face was found.')
        return dev

def determineProtocolSupport(dev):
    print('Determining Android accessory protocol support.')
    request_type = usb.util.CTRL_TYPE_VENDOR | usb.util.CTRL_IN
    GET_PROTOCOL = 51
    request = GET_PROTOCOL
    val = 0
    index = 0
    data = 2
    version = dev.ctrl_transfer(request_type, request, val, index, data)
    if version is 0:
        print('The Android accessory protocol is not supported. Exiting.')
        sys.exit()
    else:
        print('The Android accessory protocol is supported.')

def sendAccessoryInfoStr(dev, str_id, str):
    request_type = usb.util.CTRL_TYPE_VENDOR | usb.util.CTRL_OUT
    SEND_STRING = 52
    request = SEND_STRING
    val = 0
    index = str_id
    data = str
    expectedLen = len(data)
    actualLen = dev.ctrl_transfer(request_type, request, val, index, data)
    if expectedLen != actualLen:
        print('Identifying the accessory did not work! Exiting.')
        sys.exit()

def identifyDogbot(dev):
    MANUFACTURER_STR_ID = 0
    MODEL_STR_ID = 1
    DESC_STR_ID = 2
    VERSION_STR_ID = 3
    URI_STR_ID = 4
    SERIAL_NUM_STR_ID = 5
    strs = {
        MANUFACTURER_STR_ID: 'Deuterium Labs',
        MODEL_STR_ID: 'DogBot',
        DESC_STR_ID: 'A test accessory',
        VERSION_STR_ID: '1.0',
        URI_STR_ID: 'http://dogbotblog.tumblr.com',
        SERIAL_NUM_STR_ID: '42',
    }
    for str_id, str in strs.items():
        sendAccessoryInfoStr(dev, str_id, str);

def requestAccessoryMode(dev):
    request_type = usb.util.CTRL_TYPE_VENDOR | usb.util.CTRL_OUT
    START = 53
    request = START
    val = 0
    index = 0
    data = None
    dev.ctrl_transfer(request_type, request, val, index, data)

def putInAccessoryMode(dev):
    determineProtocolSupport(dev)
    identifyDogbot(dev)
    requestAccessoryMode(dev)

def main():
    print('Attempting to connect to the face.')
    dev = findDevice()
    putInAccessoryMode(dev)

if __name__ == '__main__':
    main()
