#!/usr/bin/python

# Evelyn and Dogbot
# https://youtu.be/2bm-nskEfAw

import RPi.GPIO as GPIO
import raspirobotboard
import time

def wait_for_robot_placement():
    PLACEMENT_DELAY = 20 # seconds
    time.sleep(PLACEMENT_DELAY)
    
def move_forward(robot):
    robot.reverse(3) # The robot is backwards
    robot.stop()

def move_forward(robot):
    robot.forward(3) # The robot is backwards
    robot.stop()

def spin_left(robot):
    robot.right(10) # The robot is backwards
    robot.stop()

def spin_right(robot):
    robot.left(10) # The robot is backwards
    robot.stop()

if __name__ == "__main__":
    robot = raspirobotboard.RaspiRobot()
    wait_for_robot_placement()
    move_forward(robot)
    time.sleep(1)
    move_backward(robot)
    time.sleep(1)
    spin_left(robot)
    time.sleep(1)
    spin_right(robot)
    GPIO.cleanup()
