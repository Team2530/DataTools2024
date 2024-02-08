// TODO: parse constants
use std::fs::File;
use std::io::{self, Read, BufReader};
use flate2::read::GzDecoder;

use serde::{Serialize};

#[derive(Serialize, Debug)]
pub struct LogHeader {
    pub team_number: String,
    pub log_version: u8,
    pub start_time: String,
    pub constants: Vec<ConstantData>
}

pub struct ConstantData {
    // TODO ...
}

pub struct DataFrame {
    /*
    data.writeDouble(swerve.getHeading());

    for(int smi = 0; smi < 4; smi ++) {
        final SwerveModule swerveModule = swerve.modules[smi];

        data.writeDouble(swerveModule.driveMotor.get());

        data.writeDouble(swerveModule.getDrivePosition());
        data.writeDouble(swerveModule.getDriveVelocity());

        data.writeDouble(swerveModule.steerMotor.get());
        data.writeDouble(swerveModule.getSteerPosition());
        data.writeDouble(swerveModule.getSteerVelocity());

        data.writeDouble(swerveModule.steerPID.getP());
        data.writeDouble(swerveModule.steerPID.getI());
        data.writeDouble(swerveModule.steerPID.getD());
    }
     */

    pub swerve_heading: f64,
    
    pub drive_motor_values: Vec<f64>,
    pub drive_motor_positions: Vec<f64>,
    pub drive_motor_velocities: Vec<f64>,
    
    pub steer_motor_values: Vec<f64>,
    pub steer_motor_positions: Vec<f64>,
    pub steer_motor_velocities: Vec<f64>,

    pub swerve_p: Vec<f64>,
    pub swerve_i: Vec<f64>,
    pub swerve_d: Vec<f64>,
}

pub struct Log {
    pub header: LogHeader,
    pub frames: Vec<DataFrame>
}

fn bytes_to_hex(bytes: Vec<u8>) -> String {
    bytes.iter().map(|byte| format!("{:02x}", byte)).collect()
}

fn bytes_to_i16(bytes: &[u8]) -> i16 {
    ((bytes[0] as i16) << 8) | (bytes[1] as i16)
}

// Function to read a specified number of bytes and return them as a vector
fn read_n_bytes<R: Read>(reader: &mut R, n: usize) -> io::Result<Vec<u8>> {
    let mut buffer = vec![0; n];
    match reader.read_exact(&mut buffer) {
        Ok(_) => Ok(buffer),
        Err(e) => Err(e),
    }
}


// Function to read a string with a length prefix
fn read_string_bytes<R: Read>(reader: &mut R) -> io::Result<String> {
    let string_len = read_n_bytes(reader, 2)?;
    let string_len = bytes_to_i16(&string_len);
    let string_bytes = read_n_bytes(reader, string_len as usize)?;
    let string = String::from_utf8(string_bytes).expect("Invalid UTF-8");

    Ok(string)
}

pub fn parse_log_header<R: Read>(mut reader: &mut BufReader<R>) -> io::Result<LogHeader> {
    
    let team_bytes = read_n_bytes(&mut reader, 2)?;
    let team_number = bytes_to_hex(team_bytes);

    let log_version = read_n_bytes(&mut reader, 1)?[0];

    let start_time_string = read_string_bytes(&mut reader)?;
    
    //TODO: constants

    //TODO: constants

    //TODO: constants

    //TODO: constants

    //TODO: constants


    Ok(LogHeader {
        team_number: team_number,
        log_version,
        start_time: start_time_string,
        constants: Vec::new()
    })
}

pub fn parse_log_frames<R: Read>(reader: &mut BufReader<R>) -> io::Result<Vec<DataFrame>> {
    Ok(Vec::new())
}

pub fn parse_logfile(path: &str) -> io::Result<Log> {
    let file = File::open(path)?;
    let file = GzDecoder::new(file);
    let mut reader: BufReader<GzDecoder<File>> = BufReader::new(file);
    
    let header: LogHeader = parse_log_header(&mut reader)?;
    let frames: Vec<DataFrame> = parse_log_frames(&mut reader)?;

    unimplemented!("Logfile parsing is not yet finished!");
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn parse_logfile_test() {
        let mut d = env!("CARGO_MANIFEST_DIR").to_string();
        d.push_str("/src/logfile");
        assert_eq!(parse_logfile(&d).unwrap().team_number, "2530");
        assert_eq!(parse_logfile(&d).unwrap().log_version, 1);
        assert_eq!(parse_logfile(&d).unwrap().start_time, "2024-02-02T21:39:58.967449304Z");
    }
}