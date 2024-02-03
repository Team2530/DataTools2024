use std::fs::File;
use std::io::{self, Read, BufReader};
use flate2::read::GzDecoder;

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

pub struct LogHeader {
    pub team_number: String,
    pub log_version: u8,
    pub start_time: String,
}

// Function to read a string with a length prefix
fn read_string_bytes<R: Read>(reader: &mut R) -> io::Result<String> {
    let string_len = read_n_bytes(reader, 2)?;
    let string_len = bytes_to_i16(&string_len);
    println!("string length: {}", string_len);
    let string_bytes = read_n_bytes(reader, string_len as usize)?;
    let string = String::from_utf8(string_bytes).expect("Invalid UTF-8");

    Ok(string)
}

pub fn parse_logfile(path: &str) -> io::Result<LogHeader> {
    let file = File::open(path)?;
    let file = GzDecoder::new(file);
    let mut reader = BufReader::new(file);


    let team_bytes = read_n_bytes(&mut reader, 2)?;
    let team_number = bytes_to_hex(team_bytes);
    println!("Team number: {}", team_number);

    let log_version = read_n_bytes(&mut reader, 1)?[0];
    println!("Log version: {}", log_version);

    let start_time_string = read_string_bytes(&mut reader)?;
    println!("DateTime: {}", start_time_string);
    
    Ok(LogHeader {
        team_number: team_number,
        log_version,
        start_time: start_time_string,
    })
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