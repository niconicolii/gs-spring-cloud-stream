import { LineChart, Line, XAxis, YAxis, Tooltip, Legend } from 'recharts';
import { format } from 'date-fns'

export function DemandChart({ demandData }) {

    function datetimeFormatter(value, index) {
        const datetime = new Date(value)
        if (demandData.length >= 24*12*3) {
            return format(datetime, 'MMM dd')
        } 
        return format(datetime, 'MMM dd, HH:mm')
    }

    return (
        <LineChart width={700} height={500} data={demandData}>
            <XAxis 
                dataKey="timestamp" 
                angle={30}
                tickMargin={10}
                tickFormatter={(value, index) => datetimeFormatter(value, index)}
                interval={Math.trunc(demandData.length/12)}/>
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="value" stroke="#8884d8" dot={false} />
        </LineChart>

    )
}